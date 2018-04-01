<?php

namespace {{ ModuleName }}\Action;

use Xsv\Base\Action\AbstractRestAction;
use Interop\Http\ServerMiddleware\DelegateInterface;
use Interop\Http\ServerMiddleware\MiddlewareInterface as ServerMiddlewareInterface;
use Psr\Http\Message\ServerRequestInterface;
use Zend\Diactoros\Response\JsonResponse;
use {{ ModuleName }}\InputFilter\Update{{ ResourceName }}InputFilter;
use {{ ModuleName }}\Service\Update{{ ResourceName }}DoctrineService;

class Update{{ ResourceName }}Action extends AbstractRestAction implements ServerMiddlewareInterface
{
    /** @var Update{{ ResourceName }}InputFilter */
    protected $update{{ ResourceName }}InputFilter;

    /** @var Update{{ ResourceName }}DoctrineService */
    protected $update{{ ResourceName }}Service;

    /**
     * Update{{ ResourceName }}Action constructor.
     * @param Update{{ ResourceName }}InputFilter $update{{ ResourceName }}InputFilter
     * @param Update{{ ResourceName }}DoctrineService $update{{ ResourceName }}Service
     */
    public function __construct(Update{{ ResourceName }}InputFilter $update{{ ResourceName }}InputFilter, Update{{ ResourceName }}DoctrineService $update{{ ResourceName }}Service)
    {
        $this->update{{ ResourceName }}InputFilter = $update{{ ResourceName }}InputFilter;
        $this->update{{ ResourceName }}Service = $update{{ ResourceName }}Service;
    }

    public function process(ServerRequestInterface $request, DelegateInterface $delegate)
    {
        $id = $request->getAttribute('id', '');
        $data = $this->getParsedBody($request);
        $data['id'] = $id;
        if ($this->dataNotValid($this->update{{ ResourceName }}InputFilter, $data)) {
            return $this->validationErrorResponse($this->update{{ ResourceName }}InputFilter);
        }

        $this->update{{ ResourceName }}Service->update($data);

        return new JsonResponse(1);
    }
}