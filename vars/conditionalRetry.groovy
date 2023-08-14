def call(Integer maxRetries, String filename, String[] match, Integer retrySleepMs = 7500, Boolean expBackoff = true, body) {
    def config = [:]
    def retries = 0
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config

    retry (maxRetries) {
        if (retries == 0 || match.any{el -> readFile(filename).contains(el)}) {
            if (retries > 0 && retrySleepMs > 0) {
                if (expBackoff) {
                    // sleep(retrySleep * retries)
                    sleep time: retrySleepMs * retries, unit: 'MILLISECONDS'
                } else {
                    // sleep(retrySleep)
                    sleep time: retrySleepMs, unit: 'MILLISECONDS'
                }
            }
            retries += 1
            body()
        } else {
            error("Build doesn't fulfill conditional to retry.")
        }
    }
}
