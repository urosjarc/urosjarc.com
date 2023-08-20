export interface StatusUpdateReq {
	sekund: number
	tip: 'NEZACETO' | 'NERESENO' | 'NAPACNO' | 'PRAVILNO'
}