def call(Integer retries = 5, String filename, String[] match, Integer retrySleep = 10, Boolean expBackoff = true, body) {
    def config = [:]
    def retryIndex = 0
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config

    retry (retries) {
        if (retryIndex == 0 || match.any{el -> readFile(filename).contains(el)}) {
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
            error("Skipping retry : build doesn't fulfill condition to retry.")
        }
    }
}
