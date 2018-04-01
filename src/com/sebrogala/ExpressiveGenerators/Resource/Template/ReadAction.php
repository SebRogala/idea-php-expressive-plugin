<?php

namespace {{ ModuleName }}\Action;

use Xsv\Base\Action\AbstractRestAction;
use Interop\Http\ServerMiddleware\DelegateInterface;
use Interop\Http\ServerMiddleware\MiddlewareInterface as ServerMiddlewareInterface;
use Psr\Http\Message\ServerRequestInterface;
use Zend\Diactoros\Response\JsonResponse;
use {{ ModuleName }}\Service\Read{{ ResourceName }}DoctrineService;

class Read{{ ResourceName }}Action extends AbstractRestAction implements ServerMiddlewareInterface
{
    /** @var Read{{ ResourceName }}DoctrineService */
    protected $read{{ ResourceName }}Service;

    /**
     * Read{{ ResourceName }}Action constructor.
     * @param Read{{ ResourceName }}DoctrineService $read{{ ResourceName }}Service
     */
    public function __construct(Read{{ ResourceName }}DoctrineService $read{{ ResourceName }}Service)
    {
        $this->read{{ ResourceName }}Service = $read{{ ResourceName }}Service;
    }

    public function process(ServerRequestInterface $request, DelegateInterface $delegate)
    {
        $id = (int)$request->getAttribute('id', 0);

        $queryParams = $request->getQueryParams();

        $response = [];
        if(!empty($id)) {
            $response = $this->read{{ ResourceName }}Service->findById($id);
        } else if(!empty($queryParams)) {
            $response = $this->read{{ ResourceName }}Service->find($queryParams);
        }

        return new JsonResponse($response);
    }
}