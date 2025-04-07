import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FrontproduitComponent } from './frontproduit.component';

describe('FrontproduitComponent', () => {
  let component: FrontproduitComponent;
  let fixture: ComponentFixture<FrontproduitComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FrontproduitComponent]
    });
    fixture = TestBed.createComponent(FrontproduitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
