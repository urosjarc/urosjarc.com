function randChar() {
  let items = `!"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[C]^_abcdefghijklmnopqrstuvwxyz{|}~`;
  let index = Math.floor(Math.random() * items.length);
  return items.substring(index, index + 1);
}

function scramble(ele, timeout, maxWordSize, originalWord, charIter) {
  if (originalWord == null) originalWord = ele.textContent;
  if (charIter == null) {
    charIter = {};
    let i = 0;
    for (let char of originalWord) {
      if (char !== " ") {
        if (i === 0) charIter[i] = Math.ceil(maxWordSize);
        else charIter[i] = Math.ceil(maxWordSize / originalWord.length);
      }
      i++;
    }
  }

  let newWord = "";
  let run = false;
  for (let i = 0; i < originalWord.length; i++) {
    if (i in charIter) {
      run = true;
      newWord += randChar();
      if (charIter[i] < 0) delete charIter[i];
    } else {
      newWord += originalWord.charAt(i);
    }
  }

  let keys = Object.keys(charIter);
  if (keys.length > 0) charIter[keys[0]]--;

  setTimeout(() => {
    ele.textContent = newWord;
    if (run) scramble(ele, timeout, maxWordSize, originalWord, charIter);
  }, timeout);
}

export function hacker(delay) {
  let elements = document.getElementsByClassName("hacker");
  let maxWordSize = 0;
  for (let ele of elements) {
    if(ele.textContent.length > maxWordSize){
      maxWordSize = ele.textContent.length;
    }
  }
  setTimeout(() => {
    for (let ele of elements) {
      scramble(ele, 5, maxWordSize);
    }
  }, delay);
}