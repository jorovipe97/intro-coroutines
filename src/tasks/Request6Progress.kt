package tasks

import contributors.*

suspend fun loadContributorsProgress(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit
) {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

    val totalContributors = mutableListOf<User>()
    for ((index, repo) in repos.withIndex()) {
        val contributors = service
            .getRepoContributors(req.org, repo.name)
            .also { logUsers(repo, it) }
            .bodyList()

        totalContributors += contributors
        val isCompleted = index == repos.lastIndex
        if (isCompleted) {
            println("Last item")
        }
        updateResults(totalContributors.aggregate(), isCompleted)
    }
}
