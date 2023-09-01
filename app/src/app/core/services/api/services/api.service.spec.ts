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

  it('mora dobiti uspešno nazaj podatke', () => {
    // TODO: kako ustvariti IndexRes objekt ?
    const testData: {} = {};

    // Act: Make the API call
    service.get$Response().subscribe((response) => {
      // Assert: Check if the response matches the test data
      console.log(response.body, 'BODY')
    });

    // Expect a single GET request and respond with test data
    // const req = httpMock.expectOne(/* Expected URL for your API call */);
    // expect(req.request.method).toBe('GET');
    // req.flush(testData);
  });

});
