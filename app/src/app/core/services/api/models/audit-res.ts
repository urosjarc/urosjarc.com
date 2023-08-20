import { Audit } from './audit';
import { Status } from './status';
import { Test } from './test';
export interface AuditRes {
	audit: Audit
	status: Status
	test: Test
}