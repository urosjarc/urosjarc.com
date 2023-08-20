export interface ErrorRes {
	info: string
	napaka: 'UPORABNISKA' | 'SISTEMSKA'
	status: string
}