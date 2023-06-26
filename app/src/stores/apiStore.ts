import {token} from "./tokenStore";
import {adjacencyList} from "../libs/mapping";

const DOMAIN = "http://localhost:8080"

function GET(url: string, cb) {
  return () => new Promise(async (resolve, reject) => {
    fetch(`${DOMAIN}/${url}`, {
      method: "GET",
      headers: {
        'Authorization': `Bearer ${token.get()}`
      }
    }).then(async res => {
      if (!res.ok) reject()
      res.json()
        .then(data => resolve(cb(data)))
        .catch(err => reject())
    }).catch(err => reject())
  })
}

function GET_IDENTITY(url: string): () => Promise<Array<object>> {
  return GET(url, (x) => x)
}

function GET_ADJACENCY_LIST(url: string): () => Promise<LinkedList> {
  return GET(url, adjacencyList)
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
    whois: GET_IDENTITY("auth/whois"),
  },
  profil: {
    oseba: GET_ADJACENCY_LIST("profil/oseba"),
    ucenje: GET_ADJACENCY_LIST("profil/ucenje"),
    sporocila: GET_ADJACENCY_LIST("profil/sporocila"),
    statusi: GET_ADJACENCY_LIST("profil/statusi"),
  }
}
