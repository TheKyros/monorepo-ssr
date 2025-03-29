import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Example, CreateExampleRequest } from '../../models/example.model';
import { ExampleService } from '../../services/example.service';

@Component({
  selector: 'app-example-form',
  templateUrl: './example-form.component.html',
  styleUrls: ['./example-form.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
})
export class ExampleFormComponent implements OnInit {
  form: FormGroup;
  isEditMode = false;
  loading = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private exampleService: ExampleService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      description: [''],
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.loadExample(parseInt(id));
    }
  }

  loadExample(id: number): void {
    this.loading = true;
    this.exampleService.getById(id).subscribe({
      next: (example) => {
        this.form.patchValue(example);
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Error loading example';
        this.loading = false;
      },
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.loading = true;
      const exampleData = this.form.value;

      if (this.isEditMode) {
        const id = parseInt(this.route.snapshot.paramMap.get('id')!);
        this.exampleService.update(id, exampleData).subscribe({
          next: () => this.router.navigate(['/examples']),
          error: (error) => {
            this.error = 'Error updating example';
            this.loading = false;
          },
        });
      } else {
        this.exampleService.create(exampleData).subscribe({
          next: () => this.router.navigate(['/examples']),
          error: (error) => {
            this.error = 'Error creating example';
            this.loading = false;
          },
        });
      }
    }
  }

  getErrorMessage(field: string): string {
    const control = this.form.get(field);
    if (control?.hasError('required')) {
      return `${field} is required`;
    }
    return '';
  }
}
