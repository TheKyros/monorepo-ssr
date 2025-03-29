import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Example } from '../../models/example.model';
import { ExampleService } from '../../services/example.service';

@Component({
  selector: 'app-example-list',
  templateUrl: './example-list.component.html',
  styleUrls: ['./example-list.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule],
})
export class ExampleListComponent implements OnInit {
  examples: Example[] = [];
  loading = false;
  error = '';

  constructor(private exampleService: ExampleService) {}

  ngOnInit(): void {
    this.loadExamples();
  }

  loadExamples(): void {
    this.loading = true;
    this.exampleService.getAll().subscribe({
      next: (data) => {
        this.examples = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Error loading examples';
        this.loading = false;
      },
    });
  }

  deleteExample(id: number): void {
    if (confirm('Are you sure you want to delete this example?')) {
      this.exampleService.delete(id).subscribe({
        next: () => {
          this.examples = this.examples.filter((example) => example.id !== id);
        },
        error: (error) => {
          this.error = 'Error deleting example';
        },
      });
    }
  }
}
