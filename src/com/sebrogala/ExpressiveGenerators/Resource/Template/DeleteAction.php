<?php

namespace {{ ModuleName }}\Action;

use Xsv\Base\Action\AbstractRestAction;
use Interop\Http\ServerMiddleware\DelegateInterface;
use Interop\Http\ServerMiddleware\MiddlewareInterface as ServerMiddlewareInterface;
use Psr\Http\Message\ServerRequestInterface;
use Zend\Diactoros\Response\JsonResponse;
use {{ ModuleName }}\Service\Delete{{ ResourceName }}DoctrineService;

class Delete{{ ResourceName }}Action extends AbstractRestAction implements ServerMiddlewareInterface
{
    /** @var Delete{{ ResourceName }}DoctrineService */
    protected $delete{{ ResourceName }}Service;

    /**
     * Delete{{ ResourceName }}Action constructor.
     * @param Delete{{ ResourceName }}DoctrineService $delete{{ ResourceName }}Service
     */
    public function __construct(Delete{{ ResourceName }}DoctrineService $delete{{ ResourceName }}Service)
    {
        $this->delete{{ ResourceName }}Service = $delete{{ ResourceName }}Service;
    }

    public function process(ServerRequestInterface $request, DelegateInterface $delegate)
    {
        $id = (int)$request->getAttribute('id', 0);
        $this->delete{{ ResourceName }}Service->delete($id);

        return new JsonResponse(1);
    }
}