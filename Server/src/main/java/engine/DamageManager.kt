package engine

class DamageManager {
    val EntityHPs: MutableMap<Int, Int> = emptyMap<Int, Int>().toMutableMap()

    fun assignHP(id: Int) {
        EntityHPs[id] = 280
    }
    fun dealDamage(id: Int, damage: Int) {
        if (EntityHPs[id] == null) return
        EntityHPs[id] = EntityHPs[id]!! - damage

        if (EntityHPs[id]!! <= 0f) {
            assignHP(id)
            //TODO:tp to island

        }
    }
//    fun onCollisionDamage(CollisionEvent) {
//
//    }
}