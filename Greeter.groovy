@RestController
class Greeter {
	@RequestMapping('/{who}')
	String greet(
		@PathVariable String who) {
		
		return [
			time: new Date(),
			msg: "Hello $who"
		]
	}
}
