import {token} from "./tokenStore";

const DOMAIN = "http://localhost:8080"

function GET<T>(url: string): () => Promise<T> {
  return () => new Promise(async (resolve, reject) => {
    fetch(`${DOMAIN}/${url}`, {
      method: "GET",
      headers: {
        'Authorization': `Bearer ${token.get()}`
      }
    }).then(async res => {
      if (!res.ok) reject()
      res.json()
        .then((data: T) => resolve(data))
        .catch(err => reject())
    }).catch(err => reject())
  })
}

function POST(url: string, body: object) {
  return new Promise(async (resolve, reject) => {
    fetch(`${DOMAIN}/${url}`, {
      method: "POST",
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token.get()}`
      },
      body: JSON.stringify(body)
    }).then(async res => {
      if (!res.ok) reject()
      res.json()
        .then(data => resolve(data))
        .catch(err => reject())
    }).catch(err => reject())
  })
}

function DELETE(url: string, body: object) {
  return new Promise(async (resolve, reject) => {
    fetch(`${DOMAIN}/${url}`, {
      method: "DELETE",
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token.get()}`
      },
      body: JSON.stringify(body)
    }).then(async res => {
      if (!res.ok) reject()
      res.json()
        .then(data => resolve(data))
        .catch(err => reject())
    }).catch(err => reject())
  })
}

export const api = {
  auth: {
    prijava: (body) => POST("auth/prijava", body),
    whois: GET("auth/whois"),
  },
  profil: {
    oseba: GET("profil/oseba"),
    ucenje: GET("profil/ucenje"),
    sporocila: GET("profil/sporocila"),
    statusi: GET("profil/statusi"),
  }
}
