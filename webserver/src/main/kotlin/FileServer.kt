
import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath

import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class FileServer {

    @Throws(IOException::class)
    fun run(socket: ServerSocket, fs: VFilesystem) {
        socket.use {
            while (true) {
                val s = it.accept()
                handle(s, fs)
            }
        }
    }

    private fun handle(socket: Socket, fs: VFilesystem) {
        socket.use { s ->
            val reader = s.getInputStream().bufferedReader()
            val clientRequest = reader.readLine()

            val writer = PrintWriter(s.getOutputStream())
            val response = getResponse(clientRequest, fs)
            writer.println(response)
            writer.flush()
        }
    }
    
    private fun getResponse(clientRequest: String, fs: VFilesystem): String {
        val request = clientRequest.subSequence(0, 3)
        val path = VPath(clientRequest.split(" ")[1])
        val file = fs.readFile(path)
        if (request == "GET") {
            if (file != null) {
                return "HTTP/1.0 200 OK\r\n" +
                        "Server: FileServer\r\n" +
                        "\r\n" +
                        "$file\r\n"
            }
        }
        return "HTTP/1.0 404 Not Found\r\n\n" +
                "Server: FileServer\r\n\n" +
                "\r\n"
    }
}
