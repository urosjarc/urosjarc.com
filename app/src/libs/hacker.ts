function randChar() {
  let items = `!"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[C]^_abcdefghijklmnopqrstuvwxyz{|}~`;
  let index = Math.floor(Math.random() * items.length);
  return items.substring(index, index + 1);
}

function createCharIterator(string: string, maxIterations: number): Map<number, number> {
  let charIter: Map<number, number> = new Map()
  string.split('').forEach((char, i) => {
    if (char != ' ') {
      if (i == 0) charIter.set(i, maxIterations)
      else charIter.set(i, maxIterations / string.length)
    }
  });
  return charIter
}

function scramble(element: Element, timeout: number, originalWord: string, charIter: Map<number, number>) {
  /**
   * Calculate new word base on charIter
   */
  let newWord = ""
  originalWord.split('').forEach((char, i) => {
    newWord += charIter.has(i) ? randChar() : originalWord.charAt(i)
  })

  /**
   * Lower number of iterations and remove all that are lower than 0
   */
  let i = 0
  charIter.forEach((value, key) => {
    if (i == 0) charIter.set(key, value - 1)
    if (value < 0) charIter.delete(key)
    i++
  })

  /**
   * Stop if no iterations is left
   */
  if (charIter.size == 0) {
    element.textContent = originalWord
    return
  }

  /**
   * Repeat
   */
  element.textContent = newWord
  setTimeout(() => {
    scramble(element, timeout, originalWord, charIter)
  }, timeout)
}

function maxTextContentSize(elements: HTMLCollectionOf<Element>) {
  let maxWordSize = 0;
  for (let ele of elements) {
    if (ele.textContent == null) continue
    if (ele.textContent.length > maxWordSize) {
      maxWordSize = ele.textContent.length;
    }
  }
  return maxWordSize
}

export function hacker(delay: number) {
  let elements = document.getElementsByClassName("hacker");
  let maxWordSize = maxTextContentSize(elements);

    for (let ele of elements) {
      if (ele.textContent == null) continue
      let originalWord = ele.textContent
      scramble(ele, 5, originalWord,
        createCharIterator(originalWord, maxWordSize)
      )
    }
}
