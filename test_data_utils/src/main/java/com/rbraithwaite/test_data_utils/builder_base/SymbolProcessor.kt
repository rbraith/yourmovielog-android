package com.rbraithwaite.test_data_utils.builder_base

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSName
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.validate
import java.util.Locale

class BaseBuilderProcessor(private val env: SymbolProcessorEnvironment): SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val relevantClasses = resolver
            .getSymbolsWithAnnotation(BaseBuilder::class.qualifiedName.toString())
            .filterIsInstance<KSClassDeclaration>()

        if (!relevantClasses.iterator().hasNext()) return emptyList()

        for (relevantClass in relevantClasses) {
            processClass(relevantClass)
        }

        return relevantClasses.filterNot { it.validate() }.toList()
    }

    private fun processClass(annotatedClass: KSClassDeclaration) {
        val sources = mutableListOf<KSFile>()

        val packageName = annotatedClass.packageName
        val annotatedClassName = annotatedClass.simpleName.asString()
        val baseBuilderClassName = "Base${annotatedClassName}"

        annotatedClass.containingFile?.let { sources.add(it) }

        // TODO [23-12-28 11:36p.m.] -- I should explicitly check that the type derives from TestDataBuilder.
        val dataProp = try {
            annotatedClass.getAllProperties().first {
                it.simpleName.asString() == "data"
            }
        } catch (e: NoSuchElementException) {
            env.logger.error("BaseBuilder should only be used on TestDataBuilder types.")
            throw e
        }

        val dataPropType = dataProp.type.resolve()
        val dataPropClass = dataPropType.asClass()
        // an assumption that data prop is a data class
        val dataPropConstructorParams = dataPropClass.primaryConstructor!!.parameters

        val imports = mutableSetOf<String>()
        val dataPropTypeString = preprocessType(dataPropType, imports)
        val dataPropParamTypeInfoList = mutableListOf<ParamTypeInfo>()
        for (param in dataPropConstructorParams) {
            dataPropParamTypeInfoList.add(ParamTypeInfo(
                param,
                preprocessType(param.type.resolve(), imports)
            ))
        }

        val fileContents = generateFileContents(
            packageName,
            annotatedClassName,
            baseBuilderClassName,
            imports,
            dataPropParamTypeInfoList,
            dataPropTypeString
        )

        val file = env.codeGenerator.createNewFile(
            Dependencies(
                false,
                *sources.toList().toTypedArray()
            ),
            packageName.asString(),
            "${annotatedClass.simpleName.asString()}Base"
        )
        file.write(fileContents.toByteArray())
    }

    private fun generateFileContents(
        packageName: KSName,
        annotatedClassName: String,
        baseBuilderClassName: String,
        imports: Set<String>,
        paramTypeInfoList: List<ParamTypeInfo>,
        dataPropTypeString: String
    ): String {
        return buildString {
            appendLine("package ${packageName.asString()}")

            appendLine()
            appendLine()

            imports.sortedDescending().forEach {
                appendLine("import $it")
            }

            appendLine()

            // SMELL [23-12-29 12:07a.m.] -- this <T> is terrible lmao, I'm using it to
            //  blindly downcast the setter return values.
            appendLine("abstract class $baseBuilderClassName<T>: $annotatedClassName() {")

            for ((param, paramTypeString) in paramTypeInfoList) {
                val paramName = param.name!!.getShortName()
                val methodName = "with${paramName.capitalized()}"
                val methodParam = "$paramName: $paramTypeString"
                // another data class assumption
                val methodBody = "data = data.copy($paramName = $paramName)"

                appendLine("\tfun $methodName($methodParam): T = apply { $methodBody } as T")
                appendLine()
            }

            appendLine("\toverride fun build(): $dataPropTypeString {")
            appendLine("\t\treturn data.copy()")
            appendLine("\t}") // end of build()

            appendLine("}") // end of class
        }
    }

    private fun preprocessType(ksType: KSType, outImports: MutableSet<String>): String {
        val qualifiedName = ksType.asClass().qualifiedName
        val name = qualifiedName ?: ksType.asClass().simpleName

        qualifiedName?.let { outImports.add(it.asString()) }

        return buildString {
            append(name.asString())

            val typeArgs = ksType.arguments
            if (typeArgs.isNotEmpty()) {
                append("<")

                val formattedTypeArgs = mutableListOf<String>()
                for (typeArg in typeArgs) {
                    typeArg.type?.let {
                        formattedTypeArgs.add(preprocessType(it.resolve(), outImports))
                    }
                }
                if (formattedTypeArgs.isNotEmpty()) {
                    append(formattedTypeArgs.joinToString(", "))
                }

                append(">")
            }
        }
    }

    private fun KSType.asClass(): KSClassDeclaration {
        return declaration as KSClassDeclaration
    }
}

private data class ParamTypeInfo(
    val param: KSValueParameter,
    val paramTypeString: String
)

private fun String.capitalized(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

class BaseBuilderProcessorProvider: SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return BaseBuilderProcessor(environment)
    }
}