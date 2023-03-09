package tasks

import contributors.*
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun loadContributorsChannels(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit
) = coroutineScope {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

    // rendezvous channel
    val channel = Channel<List<User>>()
    repos.forEach { repo ->
        // A producer coroutine for each repo
        launch {
            val contributors = service
                .getRepoContributors(req.org, repo.name)
                .also { logUsers(repo, it) }
                .bodyList()

            channel.send(contributors)
        }
    }

    val totalContributors = mutableListOf<User>()
    repeat(repos.size) {
        totalContributors += channel.receive()
        val isCompleted = it == repos.lastIndex
        updateResults(totalContributors.aggregate(), isCompleted)
    }
}
