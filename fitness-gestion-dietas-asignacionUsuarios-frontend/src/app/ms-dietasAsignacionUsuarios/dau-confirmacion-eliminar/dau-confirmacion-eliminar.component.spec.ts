import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmacionEliminarComponent } from './dau-confirmacion-eliminar.component';

describe('ConfirmacionEliminarComponent', () => {
  let component: ConfirmacionEliminarComponent;
  let fixture: ComponentFixture<ConfirmacionEliminarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmacionEliminarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConfirmacionEliminarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
