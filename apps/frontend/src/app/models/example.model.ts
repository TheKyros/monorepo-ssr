export interface Example {
  id: number;
  name: string;
  description?: string;
}

export interface CreateExampleRequest {
  name: string;
  description?: string;
}
