/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { RequestBuilder } from '../request-builder';

import { AdminData } from '../models/admin-data';
import { Audit } from '../models/audit';
import { AuditRes } from '../models/audit-res';
import { IndexRes } from '../models/index-res';
import { KontaktObrazecReq } from '../models/kontakt-obrazec-req';
import { KontaktObrazecRes } from '../models/kontakt-obrazec-res';
import { Napaka } from '../models/napaka';
import { NapakaReq } from '../models/napaka-req';
import { PrijavaReq } from '../models/prijava-req';
import { PrijavaRes } from '../models/prijava-res';
import { Profil } from '../models/profil';
import { StatusUpdateReq } from '../models/status-update-req';
import { TestUpdateReq } from '../models/test-update-req';
import { UcenecData } from '../models/ucenec-data';
import { UciteljData } from '../models/ucitelj-data';

@Injectable({ providedIn: 'root' })
export class ApiService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `get()` */
  static readonly GetPath = '/';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `get()` instead.
   *
   * This method doesn't expect any request body.
   */
  get$Response(
    params?: {
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<IndexRes>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.GetPath, 'get');
    if (params) {
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<IndexRes>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `get$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  get(
    params?: {
    },
    context?: HttpContext
  ): Observable<IndexRes> {
    return this.get$Response(params, context).pipe(
      map((r: StrictHttpResponse<IndexRes>): IndexRes => r.body)
    );
  }

  /** Path part for operation `adminGet()` */
  static readonly AdminGetPath = '/admin';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `adminGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  adminGet$Response(
    params?: {
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<AdminData>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.AdminGetPath, 'get');
    if (params) {
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<AdminData>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `adminGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  adminGet(
    params?: {
    },
    context?: HttpContext
  ): Observable<AdminData> {
    return this.adminGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<AdminData>): AdminData => r.body)
    );
  }

  /** Path part for operation `authPrijavaPost()` */
  static readonly AuthPrijavaPostPath = '/auth/prijava';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `authPrijavaPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  authPrijavaPost$Response(
    params: {
      body: PrijavaReq
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<PrijavaRes>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.AuthPrijavaPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<PrijavaRes>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `authPrijavaPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  authPrijavaPost(
    params: {
      body: PrijavaReq
    },
    context?: HttpContext
  ): Observable<PrijavaRes> {
    return this.authPrijavaPost$Response(params, context).pipe(
      map((r: StrictHttpResponse<PrijavaRes>): PrijavaRes => r.body)
    );
  }

  /** Path part for operation `authProfilGet()` */
  static readonly AuthProfilGetPath = '/auth/profil';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `authProfilGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  authProfilGet$Response(
    params?: {
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<Profil>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.AuthProfilGetPath, 'get');
    if (params) {
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Profil>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `authProfilGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  authProfilGet(
    params?: {
    },
    context?: HttpContext
  ): Observable<Profil> {
    return this.authProfilGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Profil>): Profil => r.body)
    );
  }

  /** Path part for operation `kontaktPost()` */
  static readonly KontaktPostPath = '/kontakt';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `kontaktPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  kontaktPost$Response(
    params: {
      body: KontaktObrazecReq
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<KontaktObrazecRes>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.KontaktPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<KontaktObrazecRes>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `kontaktPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  kontaktPost(
    params: {
      body: KontaktObrazecReq
    },
    context?: HttpContext
  ): Observable<KontaktObrazecRes> {
    return this.kontaktPost$Response(params, context).pipe(
      map((r: StrictHttpResponse<KontaktObrazecRes>): KontaktObrazecRes => r.body)
    );
  }

  /** Path part for operation `napakaPost()` */
  static readonly NapakaPostPath = '/napaka';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `napakaPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  napakaPost$Response(
    params: {
      body: NapakaReq
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<Napaka>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.NapakaPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Napaka>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `napakaPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  napakaPost(
    params: {
      body: NapakaReq
    },
    context?: HttpContext
  ): Observable<Napaka> {
    return this.napakaPost$Response(params, context).pipe(
      map((r: StrictHttpResponse<Napaka>): Napaka => r.body)
    );
  }

  /** Path part for operation `ucenecGet()` */
  static readonly UcenecGetPath = '/ucenec';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `ucenecGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  ucenecGet$Response(
    params?: {
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<UcenecData>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.UcenecGetPath, 'get');
    if (params) {
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<UcenecData>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `ucenecGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  ucenecGet(
    params?: {
    },
    context?: HttpContext
  ): Observable<UcenecData> {
    return this.ucenecGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<UcenecData>): UcenecData => r.body)
    );
  }

  /** Path part for operation `ucenecAuditGet()` */
  static readonly UcenecAuditGetPath = '/ucenec/audit';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `ucenecAuditGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  ucenecAuditGet$Response(
    params?: {
      stran?: number;
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<Array<Audit>>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.UcenecAuditGetPath, 'get');
    if (params) {
      rb.query('stran', params.stran, {});
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<Audit>>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `ucenecAuditGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  ucenecAuditGet(
    params?: {
      stran?: number;
    },
    context?: HttpContext
  ): Observable<Array<Audit>> {
    return this.ucenecAuditGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<Audit>>): Array<Audit> => r.body)
    );
  }

  /** Path part for operation `ucenecNapakaGet()` */
  static readonly UcenecNapakaGetPath = '/ucenec/napaka';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `ucenecNapakaGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  ucenecNapakaGet$Response(
    params?: {
      stran?: number;
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<Array<Napaka>>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.UcenecNapakaGetPath, 'get');
    if (params) {
      rb.query('stran', params.stran, {});
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<Napaka>>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `ucenecNapakaGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  ucenecNapakaGet(
    params?: {
      stran?: number;
    },
    context?: HttpContext
  ): Observable<Array<Napaka>> {
    return this.ucenecNapakaGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<Napaka>>): Array<Napaka> => r.body)
    );
  }

  /** Path part for operation `ucenecNapakaPost()` */
  static readonly UcenecNapakaPostPath = '/ucenec/napaka';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `ucenecNapakaPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  ucenecNapakaPost$Response(
    params: {
      stran?: number;
      body: NapakaReq
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<Napaka>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.UcenecNapakaPostPath, 'post');
    if (params) {
      rb.query('stran', params.stran, {});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Napaka>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `ucenecNapakaPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  ucenecNapakaPost(
    params: {
      stran?: number;
      body: NapakaReq
    },
    context?: HttpContext
  ): Observable<Napaka> {
    return this.ucenecNapakaPost$Response(params, context).pipe(
      map((r: StrictHttpResponse<Napaka>): Napaka => r.body)
    );
  }

  /** Path part for operation `ucenecTestTestIdPut()` */
  static readonly UcenecTestTestIdPutPath = '/ucenec/test/{test_id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `ucenecTestTestIdPut()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  ucenecTestTestIdPut$Response(
    params: {
      test_id: {
};
      body: TestUpdateReq
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<AuditRes>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.UcenecTestTestIdPutPath, 'put');
    if (params) {
      rb.path('test_id', params.test_id, {});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<AuditRes>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `ucenecTestTestIdPut$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  ucenecTestTestIdPut(
    params: {
      test_id: {
};
      body: TestUpdateReq
    },
    context?: HttpContext
  ): Observable<AuditRes> {
    return this.ucenecTestTestIdPut$Response(params, context).pipe(
      map((r: StrictHttpResponse<AuditRes>): AuditRes => r.body)
    );
  }

  /** Path part for operation `ucenecTestTestIdAuditGet()` */
  static readonly UcenecTestTestIdAuditGetPath = '/ucenec/test/{test_id}/audit';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `ucenecTestTestIdAuditGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  ucenecTestTestIdAuditGet$Response(
    params: {
      test_id: {
};
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<Array<Audit>>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.UcenecTestTestIdAuditGetPath, 'get');
    if (params) {
      rb.path('test_id', params.test_id, {});
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<Audit>>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `ucenecTestTestIdAuditGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  ucenecTestTestIdAuditGet(
    params: {
      test_id: {
};
    },
    context?: HttpContext
  ): Observable<Array<Audit>> {
    return this.ucenecTestTestIdAuditGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<Audit>>): Array<Audit> => r.body)
    );
  }

  /** Path part for operation `ucenecTestTestIdNalogaNalogaIdPost()` */
  static readonly UcenecTestTestIdNalogaNalogaIdPostPath = '/ucenec/test/{test_id}/naloga/{naloga_id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `ucenecTestTestIdNalogaNalogaIdPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  ucenecTestTestIdNalogaNalogaIdPost$Response(
    params: {
      test_id: {
};
      naloga_id: {
};
      body: StatusUpdateReq
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<AuditRes>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.UcenecTestTestIdNalogaNalogaIdPostPath, 'post');
    if (params) {
      rb.path('test_id', params.test_id, {});
      rb.path('naloga_id', params.naloga_id, {});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<AuditRes>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `ucenecTestTestIdNalogaNalogaIdPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  ucenecTestTestIdNalogaNalogaIdPost(
    params: {
      test_id: {
};
      naloga_id: {
};
      body: StatusUpdateReq
    },
    context?: HttpContext
  ): Observable<AuditRes> {
    return this.ucenecTestTestIdNalogaNalogaIdPost$Response(params, context).pipe(
      map((r: StrictHttpResponse<AuditRes>): AuditRes => r.body)
    );
  }

  /** Path part for operation `ucenecTestTestIdNalogaNalogaIdAuditGet()` */
  static readonly UcenecTestTestIdNalogaNalogaIdAuditGetPath = '/ucenec/test/{test_id}/naloga/{naloga_id}/audit';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `ucenecTestTestIdNalogaNalogaIdAuditGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  ucenecTestTestIdNalogaNalogaIdAuditGet$Response(
    params: {
      test_id: {
};
      naloga_id: {
};
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<Array<Audit>>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.UcenecTestTestIdNalogaNalogaIdAuditGetPath, 'get');
    if (params) {
      rb.path('test_id', params.test_id, {});
      rb.path('naloga_id', params.naloga_id, {});
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<Audit>>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `ucenecTestTestIdNalogaNalogaIdAuditGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  ucenecTestTestIdNalogaNalogaIdAuditGet(
    params: {
      test_id: {
};
      naloga_id: {
};
    },
    context?: HttpContext
  ): Observable<Array<Audit>> {
    return this.ucenecTestTestIdNalogaNalogaIdAuditGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<Audit>>): Array<Audit> => r.body)
    );
  }

  /** Path part for operation `uciteljGet()` */
  static readonly UciteljGetPath = '/ucitelj';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uciteljGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  uciteljGet$Response(
    params?: {
    },
    context?: HttpContext
  ): Observable<StrictHttpResponse<UciteljData>> {
    const rb = new RequestBuilder(this.rootUrl, ApiService.UciteljGetPath, 'get');
    if (params) {
    }

    return this.http.request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    ).pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<UciteljData>;
      })
    );
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `uciteljGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  uciteljGet(
    params?: {
    },
    context?: HttpContext
  ): Observable<UciteljData> {
    return this.uciteljGet$Response(params, context).pipe(
      map((r: StrictHttpResponse<UciteljData>): UciteljData => r.body)
    );
  }

}
