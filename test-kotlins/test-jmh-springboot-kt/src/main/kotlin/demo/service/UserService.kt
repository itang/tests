package demo.service

data class User(val id: Long, val name: String)

interface UserService {
    fun findById(id: Long): User
}
