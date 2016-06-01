package demo.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class UserServiceImpl : UserService {

    @Value("\${user.defaultname}")
    private lateinit var name: String

    override fun findById(id: Long): User {
        //Thread.sleep(1)
        return User(id, name.toUpperCase())
    }
}
