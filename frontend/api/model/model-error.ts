/* tslint:disable */
/* eslint-disable */
/**
 * 트렐로 클론 API
 * 트렐로 유사 서비스의 API
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


// May contain unused imports in some cases
// @ts-ignore
import type { ErrorErrorsInner } from './error-errors-inner';

/**
 * A JSON:API-compliant error response. This object is inherited by the specific response type.
 * @export
 * @interface ModelError
 */
export interface ModelError {
    /**
     * An array of Error objects
     * @type {Array<ErrorErrorsInner>}
     * @memberof ModelError
     */
    'errors': Array<ErrorErrorsInner>;
}

