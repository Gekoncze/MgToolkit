MODULE cz.mg.sample

USING cz.mg.types
USING cz.mg.console

## Just a class for testing purposes.
## Insert documentation here.
DEFINE PUBLIC CLASS Test
	PRIVATE Int32 a = "-5"
	PRIVATE Float64& b = "0.7"

	## doc doc doc
	DEFINE PUBLIC FUNCTION set INPUT Test self, Int32 newA
		Int32 buffer = self.a - newA * "10" + "1" - @b
		self.a = buffer
		print: "Debug log = %i", self.a

	DEFINE PUBLIC FUNCTION get INPUT Test self OUTPUT Int32
		RETURN self.a xxx

	DEFINE PUBLIC FUNCTION print INPUT Test self
		IF a < "0"
			print: "Value of a is negative: %i", a
		IF a > "0"
			print: "Value of a is positive: %i", a
		IF a IS "0"
			print: "Value of a is zero."

	DEFINE PUBLIC FUNCTION tet2
		set: NULL, "32"

DEFINE PUBLIC OPERATOR + INPUT Int32 a, Int32 b OUTPUT Int32
	RETURN a + b