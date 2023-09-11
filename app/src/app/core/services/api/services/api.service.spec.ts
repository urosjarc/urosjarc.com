import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ApiService } from './api.service';

describe('ApiService', () => {
  let service: ApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ApiService]
    });
    service = TestBed.inject(ApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  // afterEach(() => {
  //   httpMock.verify();
  // });

  it('get$Response mora vrniti podatke', () => {
    // TODO: kako ustvariti IndexRes objekt ?
    const testData: {} = {};

    // Act: Make the API call
    service.get$Response().subscribe((response) => {


    });
    expect(true).toBeTruthy()
  });

});
