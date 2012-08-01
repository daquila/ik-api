import com.initialkick.exceptions.BadRequestException
import com.initialkick.exceptions.InternalServerException
import com.initialkick.exceptions.NotFoundException

class UrlMappings {

	static mappings = {

		"/users" (controller:"users") {
			action = [GET:"list", POST:"create"]
		}
		
		"/users/$id" (controller:"users") {
			action = [GET:"show", PUT:"edit"]
		}
		
		"500"(controller:"httpErrors",action:"badRequest",exception:BadRequestException)
		"500"(controller:"httpErrors",action:"notFound",exception:NotFoundException)
		"500"(controller:"httpErrors",action:"internalServerError",exception:InternalServerException)
		
	}

}
