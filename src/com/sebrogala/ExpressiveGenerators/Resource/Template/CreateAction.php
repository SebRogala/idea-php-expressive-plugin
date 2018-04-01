<?php

namespace {{ ModuleName }}\Action;

use Xsv\Base\Action\AbstractRestAction;
use Interop\Http\ServerMiddleware\DelegateInterface;
use Interop\Http\ServerMiddleware\MiddlewareInterface as ServerMiddlewareInterface;
use Psr\Http\Message\ServerRequestInterface;
use Zend\Diactoros\Response\JsonResponse;
use {{ ModuleName }}\InputFilter\Create{{ ResourceName }}InputFilter;
use {{ ModuleName }}\Service\Create{{ ResourceName }}DoctrineService;

class Create{{ ResourceName }}Action extends AbstractRestAction implements ServerMiddlewareInterface
{
    /** @var Create{{ ResourceName }}InputFilter */
    protected $create{{ ResourceName }}InputFilter;

    /** @var Create{{ ResourceName }}DoctrineService */
    protected $create{{ ResourceName }}Service;

    /**
     * Create{{ ResourceName }}Action constructor.
     * @param Create{{ ResourceName }}InputFilter $create{{ ResourceName }}InputFilter
     * @param Create{{ ResourceName }}DoctrineService $create{{ ResourceName }}Service
     */
    public function __construct(Create{{ ResourceName }}InputFilter $create{{ ResourceName }}InputFilter, Create{{ ResourceName }}DoctrineService $create{{ ResourceName }}Service)
    {
        $this->create{{ ResourceName }}InputFilter = $create{{ ResourceName }}InputFilter;
        $this->create{{ ResourceName }}Service = $create{{ ResourceName }}Service;
    }

    public function process(ServerRequestInterface $request, DelegateInterface $delegate)
    {
        $data = $this->getParsedBody($request);
        if ($this->dataNotValid($this->create{{ ResourceName }}InputFilter, $data)) {
            return $this->validationErrorResponse($this->create{{ ResourceName }}InputFilter);
        }

        $this->create{{ ResourceName }}Service->create($data);

        return new JsonResponse(1);
    }
}