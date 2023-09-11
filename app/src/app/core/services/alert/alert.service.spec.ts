import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {AlertService} from './alert.service';
import {HttpErrorResponse} from '@angular/common/http';

describe('AlertService', () => {
  let alertService: AlertService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AlertService],
    });
    alertService = TestBed.inject(AlertService);
  });

  it('mora ustvariti componento AlertService', () => {
    expect(alertService).toBeTruthy();
  });
  // TODO: NEDOKONČAN TEST CASE
  it('mora prikazati error poslan v error objektu', () => {

    const errorResponse = new HttpErrorResponse({
      error: 'Error',
      status: 500,
      statusText: 'Server error',
    });

    const errorSpy = spyOn<any>(alertService, 'error');
    alertService.errorServerNiDostopen(errorResponse);
  });
});
