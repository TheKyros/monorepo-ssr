import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: '/examples', pathMatch: 'full' as const },
  {
    path: 'examples',
    loadComponent: () =>
      import('./components/example-list/example-list.component').then(
        (m) => m.ExampleListComponent
      ),
  },
  {
    path: 'examples/new',
    loadComponent: () =>
      import('./components/example-form/example-form.component').then(
        (m) => m.ExampleFormComponent
      ),
  },
  {
    path: 'examples/:id/edit',
    loadComponent: () =>
      import('./components/example-form/example-form.component').then(
        (m) => m.ExampleFormComponent
      ),
  },
];
