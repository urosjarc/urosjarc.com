export function arraysContainSameValues(arr1: string[], arr2: string[]): boolean {
    if (arr1.length !== arr2.length) {
        return false;
    }
    arr1.sort()
    arr2.sort()
    let flag = true;
    for (let i = 0; i < arr1.length; i++) {
        if (arr1[i] !== arr2[i]) {
            flag = false;
        }
    }
    return flag;
}