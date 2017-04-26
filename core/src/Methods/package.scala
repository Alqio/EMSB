/**
 * @author Kyösti Alkio
 */

package object Methods {
	
	def boolToInt(bool: Boolean) = if (bool) 1 else 0
	
	/**
	 * 1  == true
	 * 0  == false
	 * -1 == false too
	 */
	def intToBool(value: Int) = if (value == 1) true else false
	
	def irandomRange(min: Int, max: Int) = {
		val rand = util.Random
		val dist = math.abs(max - min)
		if (dist > 0)
			math.min(max, min) + rand.nextInt(dist)
		else
			min
	}

	def randomRange(min: Double, max: Double) = {
		val rand = util.Random
		val dist = math.abs(max - min)
		var num = rand.nextDouble
		while (num > dist) {
			num = rand.nextDouble
		}
		math.min(min + rand.nextDouble,max)
	}
	
  def choose[T](eka: T, toka: T) = {
    if (util.Random.nextInt(2) == 0) eka else toka
  }
  
  def lerp(eka: Int, toka: Int, prosentti: Double) = {
    (toka - eka).toDouble * prosentti
  }
  
  def lerp(eka: Double, toka: Double, prosentti: Double) = {
    (toka - eka).toDouble * prosentti
  }
  
  
}