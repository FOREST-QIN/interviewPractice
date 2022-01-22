function search(numbers, target) {
    // Your code here



    for (let i = 0; i < numbers.length; i++) {
        if (numbers[i] === target) return i;
    }
    return -1;
}



function singleMutation(str1, str2) {
    // Your code here
    if (str1 === str2) return true;
    if (Math.abs(str1.length - str2.length) > 1) return false;

    let mutations = 0;
    for (let i = 0, j = 0; i < str1.length || j < str2.length; i++, j++) {
        if (str1[i] !== str2[j]) {
            mutations++;
            if (mutations > 1) return false;
            if (str1.length > str2.length) {
                j--;
            } else if (str1.length < str2.length) {
                i--;
            }
        }
    }
    return true;
}



function getMaxProfit(prices) {
    // Your code here
    if (prices.length <= 1) return 0;
    let min = prices[0];
    let diff = 0;

    for (let i = 1; i < prices.length; i++) {
        min = Math.min(min, prices[0]);
        diff = Math.max(diff, prices[i] - min);
    }
    return diff;
}



function arraySubset(arr, sub) {
    // Your code here
    if (sub.length > arr.length) return false;

    for (let i = 0; i < sub.length; i++) {
        const index = arr.indexOf(sub[i]);
        if (index === -1) return false;
        delete arr[index];
    }
    return true;
}


function stringRotation(str1, str2) {
    // Your code here
    if (str1.length !== str2.length) return false;

    for (let i = 0; i < str1.length; i++) {
        const rotation = str1.slice(i, str1.length) + str1.slice(0, i);
        if (str2 === rotation) return true;
    }
    return false;
}

function highestFrequency(strings) {
    // Your code here
    const freqs = {};
    let max = 0;
    let string = strings[0];
    for (let i = 0; i < strings.length; i++) {
        const cur = strings[i];
        if (freqs[cur] === undefined) {
            freqs[cur] = 1;
        } else {
            freqs[cur]++;
        }

        if (freqs[cur] > max) {
            max = freqs[cur];
            string = cur;
        }
    }
    return string;
}


function isUnique(str) {
    const chars = new Set();
    for (let i = 0; i < str.length; i++) {
        const ch = str[i];
        if (chars.has(ch)) return false;
        chars.add(ch);
    }
    return true;
}

function flatten(nestedArray) {
    // Your code here
    const newArray = [];
    for (let i = 0; i < nestedArray.length; i++) {
        const item = nestedArray[i];
        if (Array.isArray(nestedArray[i])) {
            for (let j = 0; j < item.length; j++) {
                newArray.push(item[j]);
            }
        } else {
            newArray.push(item);
        }
    }
    return newArray;
}

function removeDupes(str) {
    // Your code here
    const characters = {};
    const array = [];
    for (let i = 0; i < str.length; i++) {
        const ch = str[i];
        if (!characters[ch]) {
            characters[ch] = true;
            array.push(ch);
        }
    }
    return array.join('');
}