export type BackendHealth = {
  status: string
  service: string
}

const defaultApiBaseUrl = 'http://localhost:8080'

function getApiBaseUrl() {
  return (import.meta.env.VITE_API_BASE_URL ?? defaultApiBaseUrl).replace(/\/$/, '')
}

export async function getBackendHealth(): Promise<BackendHealth> {
  const response = await fetch(`${getApiBaseUrl()}/api/health`)

  if (!response.ok) {
    throw new Error(`Health check failed with status ${response.status}`)
  }

  return response.json() as Promise<BackendHealth>
}
