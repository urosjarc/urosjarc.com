import {ComponentFixture, TestBed} from "@angular/core/testing";
import {PublicComponent} from "./public.component";
import {RouterTestingModule} from "@angular/router/testing";
import {RouterOutlet} from "@angular/router";

describe('Public komponenta testiranje', () => {
    let fixture: ComponentFixture<PublicComponent>
    let component: PublicComponent
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [RouterOutlet],
            providers: []
        })
        fixture = TestBed.createComponent(PublicComponent)
        component = fixture.componentInstance;
    })

    it('mora inicializirati komponento', () => {
        expect(component).toBeTruthy()
    })


})
