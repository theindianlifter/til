package `in`.droidose.fitnessbot.interfaces

/**
 * Created by rajatdhamija
 * 21/01/18.
 */

interface MessageInterface {
    fun addMessage(message: String)

    fun removeMessage(messagePosition: Int)
}
