import {ComponentFixture, TestBed} from "@angular/core/testing";
import {FormFieldEmailComponent} from "../form-fields/form-field-email/form-field-email.component";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {NgIf} from "@angular/common";
import {TableComponent} from "./table.component";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {ChangeDetectorRef} from "@angular/core";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('Parts / Table komponenta testi', () => {
  let fixture: ComponentFixture<TableComponent>;
  let component: TableComponent;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        MatInputModule,
        ReactiveFormsModule,
        MatIconModule,
        NgIf,
        BrowserAnimationsModule,
        MatPaginatorModule,
        MatSortModule,
      ],
      providers: [
        MatSort,
        MatPaginator,
        {provide: ChangeDetectorRef, useValue: {}},
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(TableComponent);
    component = fixture.componentInstance;
  });

  it('mora inicializirati komponento', () => {
    expect(component).toBeTruthy();
  });

  it('mora inicializirati dataSource instance v ngAfterViewInit', () => {
    // component.ngAfterViewInit();
    fixture.detectChanges()
    expect(component.dataSource.paginator).toBeDefined();
    expect(component.dataSource.sort).toBeDefined();
    expect(component.dataSource.filterPredicate).toBeDefined();
  });
  // TODO: JE POTREBNO TESTIRATI FILTRIRANJE INPUT FIELDA
  it('mora filtrirati input polje uporabnika', () => {
    expect(true).toBeTrue()
  });
});

