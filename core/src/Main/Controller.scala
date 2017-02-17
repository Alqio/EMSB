/*package Main

/**
 * @author alkiok1
 */
import scala.collection.mutable.Buffer
import processing.core._
import Methods._

class Controller extends PApplet {

	def drawOutline(str: String, x: Int, y: Int, width: Int, c: Int): Unit = {
		fill(0)
		text(str, x - width, y - width)
		text(str, x + width, y - width)
		text(str, x - width, y + width)
		text(str, x + width, y + width)
		text(str, x, y + width)
		text(str, x, y - width)
		text(str, x - width, y)
		text(str, x + width, y)
		
		fill(c)
		text(str, x, y)

	}

	var font: Option[PFont] = None

	override def settings() {
		size(1280, 720)
	}

	override def setup() {
		frameRate(60)
		background(255)
		smooth()
		font = Some(createFont("Font/04B_03__.TTF", 16, true))

		var yks = new Vihuy(this)
		var toka = new Vihuy(this)
		var torni = new SnowTower(this)
		torni.coords = new Coords(400, 480)
		toka.coords = new Coords(720, 480)
		
		World.instances += yks
		World.instances += toka
		World.instances += torni

		yks.alarms(0).time = 1

	}

	override def draw() {
		background(255)

		fill(125)
		stroke(0)
		line(0, 480, 1280, 480)
		//println(World.projectiles.size)
		World.projectiles.foreach(println)
		/** Execute the step event for all instances **/
		World.instances.foreach(_.step())
		/** Execute the step event for all projectiles **/
		World.projectiles.foreach(x => if (x != null) x.step())
		
		
		/** Move all alarms **/
		World.instances.foreach(_.alarms.foreach(_.move()))
		/** Draw all instances **/
		World.instances.foreach(_.draw())
		/** Draw all projectiles **/
		World.projectiles.foreach(x => if (x != null) x.draw())

		textFont(font.get, 24)
		fill(125)

		drawOutline("\n\nx: " + mouseX + "\ny: " + mouseY, mouseX, mouseY, 2, color(200, 28, 18))
		//text("\n\nx: " + mouseX + "\ny: " + mouseY, mouseX, mouseY)
		
		
		/** Resize the projectile buffer **/


	}
}

object Controller {
	def main(args: Array[String]) {
		PApplet.main(Array[String]("Main.Controller"))
	}
}*/