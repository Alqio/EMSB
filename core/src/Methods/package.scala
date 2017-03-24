

/**
 * @author alkiok1
 */

package object Methods {
	
	def boolToInt(bool: Boolean) = if (bool) 1 else 0
	
	/**
	 * 1  == true
	 * 0  == false
	 * -1 == false myös
	 */
	def intToBool(value: Int) = if (value == 1) true else false
	
  /*def choose(eka: String, toka: String) = {
    if (util.Random.nextInt(2) == 0) eka else toka
  }
  def choose(eka: Int, toka: Int) = { 
    if (util.Random.nextInt(2) == 0) eka else toka
  }
  def choose(eka: Double, toka: Double) = {
    if (util.Random.nextInt(2) == 0) eka else toka
  }*/
  def choose[T](eka: T, toka: T) = {
    if (util.Random.nextInt(2) == 0) eka else toka
  }
  
  def lerp(eka: Int, toka: Int, prosentti: Double) = {
    (toka - eka).toDouble * prosentti
  }
  def lerp(eka: Double, toka: Double, prosentti: Double) = {
    (toka - eka).toDouble * prosentti
  }

  def printf(s: String, vektori: Vector[AnyVal]) = {
    for (i <- s.indices) {
      if (i == '%') {

      }
    }
  }
  
  
}