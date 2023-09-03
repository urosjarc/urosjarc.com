package data

data class Piksel(
    // Coordinates
    val x: Int,
    val y: Int,

    // RGB
    val r: Int,
    val g: Int,
    val b: Int,

    // HSV
    val h: Int,
    val s: Int,
    val v: Int
) {

    companion object {
        fun is_white(p: Piksel): Boolean {
            return (p.s in 0..10)
                    && (p.v in 200..255)
        }

        fun is_black(p: Piksel): Boolean {
            return p.v in 0..50
        }

        fun is_red(p: Piksel): Boolean {
            return ((p.h in 220..255) || (p.h in 0..25))
                    && !is_white(p)
                    && !is_black(p)
        }

        fun is_red_dark(p: Piksel): Boolean {
            return is_red(p)
                    && (p.s in 130..255)
        }

        fun is_red_light(p: Piksel): Boolean {
            return is_red(p) && !is_red_dark(p)
        }
    }
}
