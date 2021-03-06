package kompile.testing

import org.junit.Test

class CompilerTest {
    @Test
    fun succeededScenario() {
        val processor = ValidProcessor()
        kotlinc()
                .withProcessors(processor)
                .addKotlin(processor.generatedFileName(), processor.generatedFileContent())
                .compile()
                .succeededWithoutWarnings()
                .generatedFile(processor.generatedFileName())
                .hasSourceEquivalentTo(processor.generatedFileContent())
    }

    @Test
    fun failedScenario() {
        val processor = ThrowExceptionProcessor()
        kotlinc()
                .withProcessors(processor)
                .addKotlin(processor.generatedFileName(), processor.generatedFileContent())
                .compile()
                .failed()
                .withErrorContaining(ThrowExceptionProcessor.errorMessage)
    }
}
