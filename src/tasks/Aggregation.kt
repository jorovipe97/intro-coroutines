package tasks

import contributors.User

/*
TODO: Write aggregation code.

 In the initial list each user is present several times, once for each
 repository he or she contributed to.
 Merge duplications: each user should be present only once in the resulting list
 with the total value of contributions for all the repositories.
 Users should be sorted in a descending order by their contributions.

 The corresponding test can be found in test/tasks/AggregationKtTest.kt.
 You can use 'Navigate | Test' menu action (note the shortcut) to navigate to the test.
*/
fun List<User>.aggregate(): List<User> {
    val totalContributions = mutableMapOf<String, User>()
    this.forEach {
        val userInMap = totalContributions.get(it.login)
        val currentContributionsCount = userInMap?.contributions ?: 0
        val newContributionsCount = currentContributionsCount + it.contributions
        totalContributions[it.login] = userInMap?.copy(contributions = newContributionsCount) ?: it
    }

    return totalContributions
        .toList()
        .sortedByDescending { it.second.contributions } // Sort users by total number of contributions
        .map { it.second } // returns a list of users
}