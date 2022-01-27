
function solution(A) {

    const map = new Map()
    let maxSum = 0
    for (let i = 0; i < A.length; i++) {
        let sum = digitSum(A[i])

        if (map.has(sum)) {
            maxSum = Math.max(maxSum, map.get(sum) + A[i])
            if (map.get(sum) < A[i]) {
                map.set(sum, A[i])
            }
        } else {
            map.set(sum, A[i])
        }

    }
    if (maxSum === 0) {
        return -1
    }
    return maxSum
}

function digitSum(num) {
    let sum = 0
    while (num !== 0) {
        sum += num % 10
        num = Math.floor(num / 10)
    }
    return sum
}

console.log([51,71,17,42])
console.log(solution([42,33,60]))
