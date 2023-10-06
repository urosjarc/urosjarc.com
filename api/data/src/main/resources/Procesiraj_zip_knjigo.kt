class Procesiraj_zip_knjigo {

    fun zdaj(fileName: String, skip: Int, end: Int) {
        val file = File("src/main/resources/$fileName")
        val omegaParts = mutableListOf<Annotation>()

        fun parse() {
            for (image in zip_iterator(file = file, skip = skip, end = end)) {
                //Popravi sliko
                val img = image.deskew()

                //Ocr sliko
                val annos = google_ocr(img)
                if (annos.size < 10) break

                //Process sliko
                val omega = Procesiraj_omego_sliko()
                omega.zdaj(img = img, annos = annos)
                omega.parse_footer()
                omega.parse_naloge()
                omega.parse_naslov()
                omega.parse_head()
                omega.parse_teorija()

                val marks = mutableListOf<Annotation>()
                if (omega.naloge.isNotEmpty())
                    marks += Annotation.map(omega.naloge.map { it.last() }, Annotation.Tip.NALOGA)
                if (omega.naslov.isNotEmpty())
                    marks.add(Annotation(omega.naslov.last(), tip = Annotation.Tip.NASLOV))

                marks.sortBy { it.ocr.average().y }

                if (omega.head.isNotEmpty()) {
                    omegaParts.last().img.append(img.getSubimage(0, 0, img.width, marks.first().ocr.yMin()))
                }

                for (i in 0 until marks.size - 1) {
                    val curr = marks[i]

                    val closestRight = marks
                        .filter { it.ocr.average().y in curr.ocr.yMin()..curr.ocr.yMax() && it.ocr.xMin() > curr.ocr.xMin() }
                        .minByOrNull { it.ocr.average().x }

                    val closestDown = marks
                        .filter { it.ocr.average().y > curr.ocr.yMax() }
                        .minByOrNull { it.ocr.average().y }

                    val x = curr.ocr.xMin()
                    val y = curr.ocr.yMin()
                    val endX = closestRight?.ocr?.xMin() ?: img.width
                    val endY = closestDown?.ocr?.yMin() ?: omega.footer.first().yMin()

                    curr.img = img.getSubimage(x, y, endX - x, endY - y)
                    omegaParts.add(curr)
                }
            }
        }

    }
}
