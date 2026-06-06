import { useEffect, useState } from 'react'
import { getBackendHealth, type BackendHealth } from '../api/health'

type HealthState =
  | { status: 'loading' }
  | { status: 'connected'; data: BackendHealth }
  | { status: 'error'; message: string }

export function BackendHealthCard() {
  const [healthState, setHealthState] = useState<HealthState>({
    status: 'loading',
  })

  useEffect(() => {
    let isCurrent = true

    getBackendHealth()
      .then((data) => {
        if (isCurrent) {
          setHealthState({ status: 'connected', data })
        }
      })
      .catch((error: unknown) => {
        if (isCurrent) {
          const message =
            error instanceof Error ? error.message : 'Unable to reach backend'
          setHealthState({ status: 'error', message })
        }
      })

    return () => {
      isCurrent = false
    }
  }, [])

  return (
    <section className="health-panel" aria-labelledby="health-title">
      <div>
        <p className="eyebrow">Backend connection</p>
        <h2 id="health-title">Service health</h2>
      </div>

      {healthState.status === 'loading' && (
        <p className="health-message">Checking backend status...</p>
      )}

      {healthState.status === 'connected' && (
        <div className="health-result health-result-up">
          <span>{healthState.data.status}</span>
          <strong>{healthState.data.service}</strong>
        </div>
      )}

      {healthState.status === 'error' && (
        <div className="health-result health-result-error">
          <span>Unavailable</span>
          <strong>{healthState.message}</strong>
        </div>
      )}
    </section>
  )
}
