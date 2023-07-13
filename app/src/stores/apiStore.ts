import {token} from "./tokenStore";
import type {data, domain} from "../types/server-core.d.ts";

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

function REQ<T>(method: string, url: string, body: object): Promise<T> {
  console.log("REQ================")
  console.log(body)
  return new Promise(async (resolve, reject) => {
    fetch(`${DOMAIN}/${url}`, {
      method: method,
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
    prijava: (body: object) => REQ("POST", "auth/prijava", body),
    whois: GET("auth/whois"),
  },
  profil: GET<data.OsebaData>("profil"),
  profil_status_update: (test_id: string, status_id: string, status: domain.Status.Tip) => REQ<domain.Status>("PUT", `profil/test/${test_id}/status/${status_id}`, {tip: status}),
  profil_status_audits: (test_id: string, status_id: string) => GET<Array<domain.Audit>>(`profil/test/${test_id}/status/${status_id}/audits`)()
}
