export function formData(e: HTMLFormElement) {
  // @ts-ignore
  const formData = new FormData(e.target)
  return Object.fromEntries(formData.entries())
}

export function dateISO(leto: number, mesec: number, dan: number) {
  try {
    return new Date(leto, mesec, dan).toISOString().split("T")[0]
  } catch (e) {
    return null
  }
}
