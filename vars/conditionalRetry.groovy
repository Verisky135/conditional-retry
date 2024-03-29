def call(Integer retries = 5, String filename="output.txt", ArrayList<String> errorMessages, Integer retrySleep = 10, Boolean expBackoff = true, body) {
    def config = [:]
    def retryIndex = 0
    def showNotMatchingError = true
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config

    retry (retries) {
        if (retryIndex == 0 || errorMessages.any{el -> readFile(filename).contains(el)}) {
            if (retryIndex > 0 && retrySleep > 0) {
                if (expBackoff) {
                    sleep(retrySleep * retryIndex)
                } else {
                    sleep(retrySleep)
                }
            }
            retryIndex += 1
            body()
        } else {
            if (showNotMatchingError) {
                showNotMatchingError = false
                error(readFile("output.txt"))
            }
            else {
                error("Skipping retry : build doesn't fulfill condition to retry.")
            }
        }
    }
}
