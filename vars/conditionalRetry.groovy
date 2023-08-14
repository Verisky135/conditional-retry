def call(Integer maxRetries, String filename, String[] matches, body) {
    def config = [:]
    def retries = 0
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config

    retry (maxRetries) {
        def match = matches.any{el -> readFile(filename).contains(el)
        println match
        if (retries == 0 || matches.any{el -> readFile(filename).contains(el)}) {
            body()
        } else {
            error("Build doesn't fulfill conditional to retry.")
        }
        retries++
    }
}
