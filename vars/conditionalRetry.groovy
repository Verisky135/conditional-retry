def call(Integer maxRetries, String filename, String[] matches, body) {
    def config = [:]
    def retries = 0
    body.resolveStrategy = Closure.OWNER_FIRST
    body.delegate = config

    retry (maxRetries) {
        def match = print matches.any{el -> readFile(filename).contains(el)
        print match
        if (retries == 0 || matches.any{el -> readFile(filename).contains(el)}) {
            body()
        } else {
            error("Build doesn't fulfill conditional to retry.")
        }
        retries++
    }
}
